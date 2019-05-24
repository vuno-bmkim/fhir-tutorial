import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;

public class Example04_Search {
     public static void main(String[] theArgs){
         // Create a client
         FhirContext ctx = FhirContext.forDstu3();
         String serverBaseUrl = "http://hapi.fhir.org/baseDstu3"; // public test server
         IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);

         // Build a search and execute it
         Bundle response = client.search()
                 .forResource(Patient.class)
                 .where(Patient.NAME.matches().value("병묵"))
                 .and(Patient.BIRTHDATE.before().day("2014-01-01"))
                 //.and(Patient.ORGANIZATION.hasAnyOfIds("1234", "12","1"))
                 .count(100)
                 .returnBundle(Bundle.class)
                 .execute();

         // How many resources did we find?
         System.out.println("Responses: " + response.getTotal());

         // Print the ID of the first one
         System.out.println("First response ID: " + response.getEntry().get(0).getResource().getId());
     }
}