import style from './DeviceWindow.module.css'
import Section from "./Section";
import {FcHighPriority, FcOk} from "react-icons/fc";
import {useEffect, useState} from "react";


let count = 0;

function DeviceWindow({activeDeviceName, deviceList}) {
      console.log(activeDeviceName);
      console.log("DeviceWindow count render = " + count++);
      let data = null;
      deviceList.map(
            (device) => {
                  console.log("getData name : " + device.comPortDto.name);
                  if (device.comPortDto.name === activeDeviceName) {
                        data = device;
                  }
            }
      )
      return (
            <div className={style.deviceWindow}>
                  {
                        data
                              ? <>
                                    <Section text={"Соединение:"}><FcOk/>
                                    </Section>
                                    <Section text={(data)
                                          ? `Версия протокола: мин. вер = ${data.protocolVersionDto.versionHi}, 
                                          макс. вер = ${data.protocolVersionDto.serialId}`
                                          : <FcHighPriority/>}
                                    />
                                    <Section text={(data)
                                          ? `ID прибора = ${data.deviceTypeDto.serialId}`
                                          : <FcHighPriority/>}/>
                                    <Section text={(data)
                                          ? `ID конвертера = ${data.converterType.serialId}`
                                          : <FcHighPriority/>}/>
                              </>
                              :<>
                                    <Section text={"COM port не выбран"}/>
                                    <Section/>
                              </>
                  }
            </div>
      )
}

export default DeviceWindow;