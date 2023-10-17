import style from './CheckBoxList.module.css'
import CheckBox from "./CheckBox";

function CheckBoxList({activeComPortName, setActiveComPortNameHandler, comPortList}) {

      return (
            <div className={style.checkBoxList}>
                  {
                        comPortList.map(
                              (device, key) => {
                                    return <CheckBox
                                          key = {key}
                                          name = {device.comPort}
                                          description = {device.comPort}
                                          activeComPortName = {activeComPortName}
                                          setActiveComPortNameHandler= {setActiveComPortNameHandler}
                                    />
                              }
                        )
                  }
            </div>
      )
}

export default CheckBoxList;