import style from './CheckBoxList.module.css'
import CheckBox from "./CheckBox";

function CheckBoxList({activeComPortName, setActiveComPortName, comPortList, setComPortList}) {
      return (
            <div className={style.checkBoxList}>
                  {
                        comPortList.map(
                              (comPort, key) => {
                                    return <CheckBox
                                          key = {key}
                                          id={comPort.id}
                                          name={comPort.name}
                                          description={comPort.description}
                                    />
                              }
                        )
                  }
            </div>
      )
}

export default CheckBoxList;