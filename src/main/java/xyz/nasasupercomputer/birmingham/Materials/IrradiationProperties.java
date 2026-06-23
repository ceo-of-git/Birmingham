package xyz.nasasupercomputer.birmingham.Materials;

public record IrradiationProperties(
        boolean shouldIrradiate,
        int irradiationRange,
        double irradiationPower
) {
}
