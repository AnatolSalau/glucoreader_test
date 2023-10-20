import style from './DeviceWindow.module.css'
import Section from "./Section";
import {FcHighPriority, FcOk} from "react-icons/fc";
import CheckBox from "../deviceList/CheckBox";
import {useState} from "react";
import {useEffect} from "react";
let count = 0;
function DeviceWindow({activeDeviceName, deviceList}) {
      console.log(activeDeviceName);
      console.log(count++);
      let data = null;
      const getData = () => {
            let data = null;
            deviceList.map(
                  (device) => {
                        if (device.comPortDto.name === activeDeviceName) {
                              data = device;
                              return data
                        }
                  }
            )
            return data;
      };
      data = getData();
      return (
            <div className={style.deviceWindow}>

                        <Section text={"Соединение:"}>
                              {(data)
                              ? <FcOk/>
                              : <FcHighPriority/>}
                        </Section>
                        <Section text= {(data)
                              ? `Версия протокола: мин. вер = ${data.protocolVersionDto.versionHi}, макс. вер = ${data.protocolVersionDto.versionLo}`
                              : <FcHighPriority/>}
                        />
                        <Section text={"Версия прибора:"}/>
                        <Section text={"Состояние прибора:"}/>
            </div>
      )
}

export default DeviceWindow;