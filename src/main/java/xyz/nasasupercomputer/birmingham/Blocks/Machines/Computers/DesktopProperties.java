package xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers;

public record DesktopProperties(
        double computePower, // How many Computing "Operations" can take place per second
        double computeSpeed,  // How fast General things load or whatever
        double powerEfficiency, // Multiplies how much power is used,
        boolean hasGuiSupport // If the GUI is the Terminal one (false) or the mouse usable one (true)
) {
}
