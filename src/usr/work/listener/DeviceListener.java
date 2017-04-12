package usr.work.listener;

public interface DeviceListener {
	public void listChange(int areaId,int flag);
	public void objectChange(int areaId,int deviceId,String field,Object oldValue,Object newValue);
}
