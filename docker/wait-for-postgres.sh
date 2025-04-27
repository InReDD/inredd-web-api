#!/bin/sh

set -e

host="$POSTGRES_HOST"
port="$POSTGRES_PORT"
timeout="${POSTGRES_TIMEOUT:-30}" # Tempo máximo de espera (30s padrão)

echo "⏳ Waiting for Postgres at $host:$port (timeout ${timeout}s)..."

start_ts=$(date +%s)
while :
do
    if pg_isready -h "$host" -p "$port" >/dev/null 2>&1; then
        echo "✅ Postgres is up at $host:$port!"
        break
    fi
    current_ts=$(date +%s)
    elapsed=$(( current_ts - start_ts ))
    if [ "$elapsed" -ge "$timeout" ]; then
        echo "❌ Timeout after ${timeout}s waiting for Postgres at $host:$port"
        exit 1
    fi
    sleep 2
done

exec "$@"