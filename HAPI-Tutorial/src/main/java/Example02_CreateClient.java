import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Patient;

public class Example02_CreateClient {
    public static void main(String[] theArgs) {
        Patient pat = new Patient();

        // Add a "name" element
        HumanName name = pat.addName();
        name.setFamily("Kim").addGiven("BM");

        // Create a context
        FhirContext ctx = FhirContext.forDstu3();

        // Create a client
        String serverBaseUrl = "http://hapi.fhir.org/baseDstu3"; // public test server
        IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

        // Use the client to store a new resource instance
        MethodOutcome outcome = client
                .create()
                .resource(pat)
                .execute();

        // Print the ID
        System.out.println(outcome.getId());
    }
}
