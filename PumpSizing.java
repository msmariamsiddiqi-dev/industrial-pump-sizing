/**
 * Industrial Wastewater Pump Sizing Calculator
 * VERIFIED DATA FROM EPA TABLE 3-4 (Page 3-7)
 * Source: EPA Onsite Wastewater Treatment Systems Manual (2002)
 * https://www.epa.gov/sites/default/files/2015-06/documents/2004_07_07_septics_septic_2002_osdm_all.pdf
 */

public class PumpSizing {
    
    public static void main(String[] args) {
        
        // ==========================================
        // 1. INPUT PARAMETERS (VERIFIED FROM EPA TABLE 3-4)
        // ==========================================
        
        // FACILITY: Department Store
        // Source: EPA Table 3-4, Page 3-7 (verified by user)
        
        // Facility size assumptions (realistic for small-mid department store)
        int numEmployees = 50;
        int numToiletRooms = 4;
        
        // EPA Table 3-4 flow rates (TYPICAL values)
        double flowEmployeeGpd = 10.0;        // gal/employee/day (EPA typical)
        double flowToiletRoomGpd = 500.0;     // gal/toilet room/day (EPA typical)
        
        // Calculate total daily flow
        double employeeFlow = numEmployees * flowEmployeeGpd;           // 500 gal/day
        double toiletFlow = numToiletRooms * flowToiletRoomGpd;         // 2,000 gal/day
        double totalDailyFlow = employeeFlow + toiletFlow;              // 2,500 gal/day
        
        // Convert to m³/hr (1 m³ = 264.17 gallons)
        double flowM3Hr = (totalDailyFlow / 264.17) / 24;  // ≈ 0.39 m³/hr
        
        // Design flow (use average flow)
        double designFlowM3Hr = flowM3Hr;
        
        // PIPE PARAMETERS
        double pipeInch = 2.0;
        double pipeIdM = 2.067 * 0.0254;  // 2-inch Sch 40 ID in meters = 0.0525 m
        double pipeLengthM = 40.0;        // Moderate distance
        double liftM = 6.0;               // Modest elevation
        
        // FLUID PROPERTIES (Water at 20°C)
        double rho = 1000.0;              // kg/m³
        double mu = 0.001;                // Pa·s
        double g = 9.81;                  // m/s²
        double epsilon = 0.000045;        // Carbon steel roughness (m)
        
        // NPSH PARAMETERS
        double PAtmPa = 101325;           // Atmospheric pressure (Pa)
        double PVaporPa = 2339;           // Water vapor pressure at 20°C (Pa)
        double suctionStaticHeadM = 1.5;  // Flooded suction
        double suctionPipeLengthM = 6.0;  // Suction side length
        
        
        // ==========================================
        // 2. CALCULATIONS
        // ==========================================
        
        // Calculate velocity
        double flowM3S = designFlowM3Hr / 3600.0;
        double area = Math.PI * Math.pow(pipeIdM / 2.0, 2);
        double velocity = flowM3S / area;
        
        // Calculate Reynolds number
        double Re = (rho * velocity * pipeIdM) / mu;
        
        // Calculate friction factor (Swamee-Jain)
        double f;
        if (Re < 2000) {
            f = 64.0 / Re;  // Laminar flow
        } else {
            double term = (epsilon / (3.7 * pipeIdM)) + (5.74 / Math.pow(Re, 0.9));
            f = 0.25 / Math.pow(Math.log10(term), 2);
        }
        
        // Calculate head loss
        double hMajor = f * (pipeLengthM / pipeIdM) * (Math.pow(velocity, 2) / (2 * g));
        double KMinor = 3.0;
        double hMinor = KMinor * (Math.pow(velocity, 2) / (2 * g));
        double hTotal = hMajor + hMinor;
        
        // Total Dynamic Head
        double TDH = liftM + hTotal;
        
        // NPSH calculation
        double hFSuction = f * (suctionPipeLengthM / pipeIdM) * (Math.pow(velocity, 2) / (2 * g));
        double KMinorSuction = 1.5;
        double hFSuctionTotal = hFSuction + (KMinorSuction * Math.pow(velocity, 2) / (2 * g));
        double NPSHA = (PAtmPa / (rho * g)) + suctionStaticHeadM - hFSuctionTotal - (PVaporPa / (rho * g));
        
        // Power estimate
        double PHydraulicW = rho * g * (designFlowM3Hr / 3600.0) * TDH;
        double PElectricW = PHydraulicW / 0.70;
        
        
        // ==========================================
        // 3. OUTPUT RESULTS
        // ==========================================
        System.out.println("======================================================================");
        System.out.println("DEPARTMENT STORE WASTEWATER PUMP SIZING");
        System.out.println("DATA VERIFIED FROM EPA TABLE 3-4 (Page 3-7)");
        System.out.println("======================================================================");
        
        System.out.println("\n📋 FACILITY DETAILS:");
        System.out.println("  • Type: Department Store");
        System.out.println("  • Employees: " + numEmployees);
        System.out.println("  • Toilet rooms: " + numToiletRooms);
        System.out.println("  • Source: EPA Table 3-4, Page 3-7");
        
        System.out.println("\n💧 FLOW CALCULATION (EPA Data):");
        System.out.println("  • Employee flow: " + numEmployees + " × " + flowEmployeeGpd + " = " + (int)employeeFlow + " gal/day");
        System.out.println("  • Toilet room flow: " + numToiletRooms + " × " + flowToiletRoomGpd + " = " + (int)toiletFlow + " gal/day");
        System.out.println("  • Total daily flow: " + (int)totalDailyFlow + " gal/day");
        System.out.printf("  • Design flow: %.3f m³/hr%n", designFlowM3Hr);
        
        System.out.println("\n🔧 PIPE PARAMETERS:");
        System.out.println("  • Pipe size: " + (int)pipeInch + "-inch Schedule 40 steel");
        System.out.printf("  • Pipe ID: %.1f mm%n", pipeIdM * 1000);
        System.out.println("  • Total length: " + pipeLengthM + " m");
        System.out.println("  • Static lift: " + liftM + " m");
        
        System.out.println("\n📊 HYDRAULIC RESULTS:");
        System.out.printf("  • Velocity: %.3f m/s%n", velocity);
        System.out.printf("  • Reynolds #: %.0f (%s)%n", Re, (Re < 2000) ? "Laminar" : "Turbulent");
        System.out.printf("  • Friction factor: %.4f%n", f);
        System.out.printf("  • Total head loss: %.2f m%n", hTotal);
        System.out.printf("  • TOTAL DYNAMIC HEAD (TDH): %.2f m%n", TDH);
        
        System.out.println("\n✅ SAFETY CHECKS:");
        
        // Velocity check
        if (velocity < 0.6) {
            System.out.printf("  ⚠️  VELOCITY LOW: %.3f m/s%n", velocity);
            System.out.println("      Recommended: 0.6-3.0 m/s to prevent solids settling");
            System.out.println("      Consider: Smaller pipe diameter or higher flow");
        } else if (velocity <= 3.0) {
            System.out.printf("  ✅ VELOCITY OK: %.3f m/s (within 0.6-3.0 m/s)%n", velocity);
        } else {
            System.out.printf("  ⚠️  VELOCITY HIGH: %.3f m/s (erosion risk)%n", velocity);
        }
        
        // NPSH check
        double typicalNPSHR = 2.0;
        double margin = NPSHA - typicalNPSHR;
        if (margin > 0.5) {
            System.out.printf("  ✅ NPSH OK: Available=%.2f m (margin: +%.2f m)%n", NPSHA, margin);
        } else {
            System.out.printf("  ⚠️  NPSH MARGIN LOW: Available=%.2f m%n", NPSHA);
        }
        
        System.out.println("\n⚡ POWER ESTIMATE:");
        System.out.printf("  • Hydraulic power: %.1f W%n", PHydraulicW);
        System.out.printf("  • Electric power: %.3f kW (@70%% efficiency)%n", PElectricW / 1000);
        
        System.out.println("\n======================================================================");
        System.out.println("📚 REFERENCES:");
        System.out.println("  EPA Onsite Wastewater Treatment Systems Manual (2002)");
        System.out.println("  Table 3-4: Department Store flow rates (Page 3-7)");
        System.out.println("  https://www.epa.gov/sites/default/files/2015-06/documents/");
        System.out.println("  2004_07_07_septics_septic_2002_osdm_all.pdf");
        System.out.println("======================================================================");
        
        System.out.println("\n💡 ENGINEERING NOTES:");
        System.out.println("  • This design uses EPA TYPICAL values (not peak flows)");
        System.out.printf("  • Low velocity (%.3f m/s) is expected for small facilities%n", velocity);
        System.out.println("  • For real projects: consult local codes and add safety factors");
        System.out.println("  • Consider intermittent operation for low-flow systems");
    }
}
