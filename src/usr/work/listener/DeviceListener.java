package usr.work.listener;

import usr.work.bean.Device;

public interface DeviceListener {
	public void listChange(int areaId,int flag);
	public void objectChange(Device device,String field,Object oldValue,Object newValue);
}
