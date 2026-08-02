package xyz.nasasupercomputer.birmingham.Blocks.Machines.Computers;

public interface IDesktopType {
	public static final DesktopProperties DESKTOP_PROPERTIES = new DesktopProperties(1.0, 1.0, 1.0, false);
	
	public DesktopProperties GetProperties();
}
