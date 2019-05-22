import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.dstu3.model.*;
import java.util.Date;
import java.util.List;

public class Example01_CreateAPatient {
    public static void main(String[] theArgs){

        // =====================================
        // 1. Create Rosource
        Patient patient = new Patient();
        Encounter encounter = new Encounter();

        // "name" element
        patient.addName()
                .setFamily("김")
                .addGiven("병묵");
        // "telecom" element
        patient.addTelecom()
                .setUse(ContactPoint.ContactPointUse.HOME)
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setValue("010-4597-3589");
        // "gender" element
        patient.setGender(Enumerations.AdministrativeGender.MALE);
        // "address" element
        patient.addAddress()
                .setCountry("Korea")
                .setCity("Seoul");
        patient.addAddress()
                .setCountry("Korea")
                .setCity("Daegu");
        // "birthDate" element
        patient.setBirthDateElement(
                new DateType("1992-04-22"));
        // =====================================


        // =====================================
        // 2. Resource Encoding
        // Create a JSON parser
        FhirContext ctx = FhirContext.forDstu3();
        IParser parser = ctx.newJsonParser();
        //parser.setPrettyPrint(true);

        String encodedPatient = parser.encodeResourceToString(patient);
        System.out.println(encodedPatient);
        // =====================================


        // =====================================
        // 3. Resource Parsing
        Patient decodedPatient = parser.parseResource(Patient.class, encodedPatient);
        List<Address> addresses = decodedPatient.getAddress();
        for (Address address: addresses){
            String country = address.getCountry();
            String city = address.getCity();
            System.out.println("country: " + country + ", city: " + city);
        }
        // =====================================


        // =====================================
        // 4. Client Create
        // Create a client
        String serverBaseUrl = "http://hapi.fhir.org/baseDstu3"; // public test server
        IGenericClient client = ctx.newRestfulGenericClient(serverBaseUrl);
        // Use the client to store a new resource instance
        MethodOutcome outcome = client
                .create()
                .resource(patient)
                .execute();
        // =====================================


        // =====================================
        // 5. get Id and reference setting
        String patientId = outcome.getId().toString();
        System.out.println(patientId);
        encounter.getSubject().setReference(patientId);
        Period period = new Period();
        Date startDate = new Date();
        Date endDate = new Date();
        period.setStart(startDate).setEnd(endDate);
        encounter.setPeriod(period);
        MethodOutcome outcome2 = client
                .create()
                .resource(encounter)
                .execute();
        System.out.println(outcome2.getId());
        // =====================================


        /* 기본 데이터 타입 사용 방법
        DateTimeType effective = new DateTimeType();
        effective.setValue(new Date());
        effective.setValue(new Date(), TemporalPrecisionEnum.MINUTE);
        effective.setValueAsString("2019-05-19T15:00:00+09:00");

        BooleanType active = new BooleanType();
        active.setValue(true);
        active.setValueAsString("true");

        DecimalType value = new DecimalType();
        value.setValue(1.2d);
        value.setValueAsString("1.20000");
        */
    }
}
