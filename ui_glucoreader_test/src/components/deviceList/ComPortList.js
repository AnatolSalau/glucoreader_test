import style from './ComPortList.module.css'
import CheckBoxList from "./CheckBoxList";
import {TbReload} from "react-icons/tb";
function ComPortList({text, activeDeviceName, setActiveDeviceNameHandler, deviceList, setDeviceListHandler}) {

      return (
            <div className={style.comPortList}>
                  <div className={style.text}>
                        {text}
                  </div>
                  <div>
                              <button
                                    onClick={() => {setDeviceListHandler()}}
                                    style={{
                                          width: "30px",
                                          height: "30px",
                                          fontSize: "30px"
                              }}
                              >
                                    <TbReload/>
                              </button>
                  </div>
                  <CheckBoxList
                        activeDeviceName={activeDeviceName}
                        setActiveDeviceNameHandler={setActiveDeviceNameHandler}
                        deviceList={deviceList}
                  />
            </div>
      )
}

export default ComPortList;