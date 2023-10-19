import style from './CheckBoxList.module.css'
import CheckBox from "./CheckBox";

function CheckBoxList({activeDeviceName, setActiveDeviceNameHandler, deviceList}) {

      return (
            <div className={style.checkBoxList}>
                  {
                        deviceList.map(
                              (device, key) => {
                                    console.log("device = " + device)
                                    return <CheckBox
                                          key = {key}
                                          name = {device.comPortDto.name}
                                          description = {device.comPortDto.description}
                                          devicePortName= {activeDeviceName}
                                          setActiveDeviceNameHandler= {setActiveDeviceNameHandler}
                                    />
                              }
                        )
                  }
            </div>
      )
}

export default CheckBoxList;