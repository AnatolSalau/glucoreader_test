import style from './ComPortList.module.css'
import CheckBoxList from "./CheckBoxList";
import {TbReload} from "react-icons/tb";
function ComPortList({text, activeDeviceName, setActiveDeviceNameHandler, deviceList, setDeviceListHandler}) {

      return (
            <div className={style.comPortList}>
                  <div className={style.text}>
                        {text}
                  </div>

                              <div
                                    onClick={() => {setDeviceListHandler()}}
                                    style={{
                                          width: "30px",
                                          height: "30px",
                                          fontSize: "30px",
                                          backgroundColor: "lightgray",
                                          boxShadow: "rgba(0, 0, 0, 0.25) 0px 2px 4px",
                                          borderRadius: "4px"
                              }}
                              >
                                    <TbReload/>
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