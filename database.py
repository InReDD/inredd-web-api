import os
from dotenv import load_dotenv
load_dotenv()

from sqlalchemy import create_engine, event
from sqlalchemy.orm import scoped_session, sessionmaker, declarative_base

postgresql_engine = create_engine(
    f"postgresql+psycopg2://{os.getenv('DATABASE_URL')}",
    pool_reset_on_return=None, # disable default reset-on-return scheme
)
db_session = scoped_session(sessionmaker(autocommit=False,
                                         autoflush=False,
                                         bind=postgresql_engine))
Base = declarative_base()
Base.query = db_session.query_property()

def init_db():
    # import all modules here that might define models so that
    # they will be registered properly on the metadata.  Otherwise
    # you will have to import them first before calling init_db()
    import app.main.model
    Base.metadata.create_all(bind=postgresql_engine)

@event.listens_for(postgresql_engine, "reset")
def _reset_postgresql(dbapi_connection, connection_record, reset_state):
    if not reset_state.terminate_only:
        dbapi_connection.execute("CLOSE ALL")
        dbapi_connection.execute("RESET ALL")
        dbapi_connection.execute("DISCARD TEMP")

    # so that the DBAPI itself knows that the connection has been
    # reset
    dbapi_connection.rollback()