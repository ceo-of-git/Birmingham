package xyz.nasasupercomputer.birmingham.Blocks.Custom;

public record IrradiationProperties(
        boolean shouldIrradiate,
        int irradiationRange,
        double irradiationPower
) {
}
