
//  DATA FROM EPA TABLE 3-4
// EPA Onsite Wastewater Treatment Systems Manual

public class Main {
    public static void main(String[] args) {

        int numEmployees = 50;
        int numToiletRooms = 4;

        // EPA Table 3-4 Design Factors
        double flowEmployeeGpd = 10.0;
        double flowToiletRoomGpd = 500.0;

        // Total daily flow calculation
        double employeeFlow = numEmployees * flowEmployeeGpd;
        double toiletFlow = numToiletRooms * flowToiletRoomGpd;
        double totalDailyFlow = employeeFlow + toiletFlow;

        // Convert to m³/hr (1 m³ = 264.17 gallons)
        double flowM3Hr = (totalDailyFlow / 264.17) / 24;
        double designFlowM3Hr = flowM3Hr; // Average design flow

        // PIPE & SYSTEM PARAMETERS
        double pipeInch = 2.0;
        double pipeIdM = 2.067 * 0.0254; // 2-inch Sch 40 ID per ASME B36.10M
        double pipeLengthM = 40.0;       // Piping run length
        double liftM = 6.0;              // Static elevation lift

        double TDH = liftM;

        System.out.println("DEPARTMENT STORE WASTEWATER PUMP SIZING (PRELIMINARY)");
        System.out.println("DATA VERIFIED FROM EPA TABLE 3-4 (Page 3-7)\n");

        System.out.println("FACILITY DETAILS:");
        System.out.println("  • Type: Department Store");
        System.out.println("  • Employees: " + numEmployees);
        System.out.println("  • Toilet rooms: " + numToiletRooms);
        System.out.println("  • Source: EPA Table 3-4, Page 3-7\n");

        System.out.println("FLOW CALCULATION (EPA Data):");
        System.out.println("  • Employee flow: " + numEmployees + " × " + flowEmployeeGpd + " = " + (int)employeeFlow + " gal/day");
        System.out.println("  • Toilet room flow: " + numToiletRooms + " × " + flowToiletRoomGpd + " = " + (int)toiletFlow + " gal/day");
        System.out.println("  • Total daily flow: " + (int)totalDailyFlow + " gal/day");
        System.out.printf("  • Design flow: %.3f m³/hr%n\n", designFlowM3Hr);

        System.out.println("PIPE & SYSTEM PARAMETERS:");
        System.out.println("  • Pipe size: " + (int)pipeInch + "-inch Schedule 40 steel");
        System.out.printf("  • Pipe ID: %.1f mm (per ASME B36.10M)%n", pipeIdM * 1000);
        System.out.println("  • Total length: " + pipeLengthM + " m");
        System.out.println("  • Static lift: " + liftM + " m\n");

        System.out.println("PRELIMINARY HYDRAULIC RESULTS:");
        System.out.printf("  • TOTAL DYNAMIC HEAD (TDH): %.2f m (Static lift baseline)%n", TDH);
        System.out.println("  • Note: Friction, minor losses & NPSH excluded for rapid screening\n");

        System.out.println("REFERENCES:");
        System.out.println("  • EPA Onsite Wastewater Treatment Systems Manual (2002)");
        System.out.println("  • ASME B36.10M: Wrought Steel Pipe Dimensions\n");

        System.out.println("DESIGN NOTES:");
        System.out.println("  • Uses EPA TYPICAL values for preliminary facility screening");
        System.out.println("  • Final design requires: peaking factors, Darcy-Weisbach losses, NPSH margin, & pump curves");
        System.out.println("  • Always validate against local plumbing codes & manufacturer data");
    }
}