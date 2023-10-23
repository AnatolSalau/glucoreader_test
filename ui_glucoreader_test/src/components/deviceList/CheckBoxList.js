import style from './CheckBoxList.module.css'
import CheckBox from "./CheckBox";
import Section from "../deviceWindow/Section";

function CheckBoxList({activeDeviceName, setActiveDeviceNameHandler, deviceList}) {

      return (
            <div className={style.checkBoxList}>
                  {
                        deviceList.length !==0
                        ? deviceList.map(
                              (device, key) => {
                                    return <CheckBox
                                          key={key}
                                          name={device.comPortDto.name}
                                          description={device.comPortDto.description}
                                          devicePortName={activeDeviceName}
                                          setActiveDeviceNameHandler={setActiveDeviceNameHandler}
                                    />
                              }
                        )
                        : <>
                              Идет загрузка...
                        </>
                  }
            </div>
      )
}

export default CheckBoxList;