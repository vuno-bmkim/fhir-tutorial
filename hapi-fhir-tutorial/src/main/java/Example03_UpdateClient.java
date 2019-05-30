import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.DateType;
import org.hl7.fhir.dstu3.model.Enumerations;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Patient;

public class Example03_UpdateClient {
    public static void main(String[] theArgs) {

        // Create a client
        FhirContext ctx = FhirContext.forDstu3();
        String serverBaseUrl = "http://hapi.fhir.org/baseDstu3"; // public test server
        IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

        // Use the client to read back the new instance using the ID we retrieved from the read
        Patient pat = client.read().resource(Patient.class).withId("1943769").execute();

        // Print the ID of the newly created resource
        System.out.println("Found ID: " + pat.getId());

        // Change the gender
        pat.setBirthDateElement(
                new DateType("2019-04-22"));

        // Use the client to store a new resource instance
        MethodOutcome outcome = client
                .update()
                .resource(pat)
                .execute();

        // Print the ID
        System.out.println(outcome.getId());
    }
}
