package api.webservices.inredd;

import api.webservices.inredd.domain.model.Radiograph;
import api.webservices.inredd.repository.RadiographRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = InreddApplication.class)
class InreddApplicationTests {

	@Autowired
	private RadiographRepository radiographRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testViewerContextJson() {
		Optional<Radiograph> radiographOptional = radiographRepository.findById(1L);
		assertNotNull(radiographOptional, "Radiograph not found");

		Radiograph radiograph = radiographOptional.orElseThrow();
		System.out.println("Radiograph ID: " + radiograph.getId());
		System.out.println("Viewer Context JSON: " + radiograph.getViewerContextJson());

		JsonNode viewerContextJson = radiograph.getViewerContextJson();
		assertNotNull(viewerContextJson, "viewerContextJson should not be null");
	}
}
