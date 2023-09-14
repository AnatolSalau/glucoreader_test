import style from './CheckBoxList.module.css'
import CheckBox from "./CheckBox";

function CheckBoxList({activeComPortName, setActiveComPortNameHandler, comPortList}) {
      return (
            <div className={style.checkBoxList}>
                  {
                        comPortList.map(
                              (comPort, key) => {
                                    return <CheckBox
                                          key = {key}
                                          id = {comPort.id}
                                          name = {comPort.name}
                                          description = {comPort.description}
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