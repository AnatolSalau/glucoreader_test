import {useEffect, useState} from "react";
import { FcOk } from 'react-icons/fc'
import style from './Main.module.css'
import Section from "./deviceWindow/Section";
import ComPortList from "./comPortList/ComPortList";
import DeviceWindow from "./deviceWindow/DeviceWindow";


function Main({connection}) {

      let [deviceList, setDeviceList] = useState([]);

      useEffect(() => {
            connection.onopen = (ev) => {
                  console.log('Connection is open');
                  if (ev.data) {
                        let json = JSON.parse(ev.data);
                        setDeviceList(json.data.deviceList);
                  }
            };
            connection.onmessage = (ev) => {
                  console.log('Message from server received');
                  let json = JSON.parse(ev.data);
                  setDeviceList(json.data.deviceList);
            };
            connection.onclose = (ev) => {
                  console.log('Connection is close');
            };

            const parseDateTimeFromStr = () => {
                  let mydate = new Date('2023-10-05T15:16:17');
                  console.log("GetDay : " + mydate.getUTCDate());
                  console.log("GetMonth : " + (mydate.getMonth() + 1));
                  console.log("GetFullYear : " + mydate.getFullYear());
                  console.log("GetHours : " + mydate.getHours());
                  console.log("GetMinutes : " + mydate.getMinutes());
                  console.log("GetSeconds : " + mydate.getSeconds());
            }
            parseDateTimeFromStr();

      }, [setDeviceList]);

      let [activeDeviceName, setActiveDeviceName] = useState("");

      const setActiveDeviceNameHandler = (name) => {
                  if (name === activeDeviceName) {
                        setActiveDeviceNameHandler("")

                  }
                  else {
                        setActiveDeviceName(name);
                        connection.send(name);
                  }
      };
      console.log("activeDeviceName : " + activeDeviceName);
      console.log(deviceList)
      return (
            <div className={style.main}>
                  <ComPortList
                        text={"Список COM портов:"}
                        deviceList={deviceList}
                        activeDeviceName={activeDeviceName}
                        setActiveDeviceNameHandlerHandler={setActiveDeviceNameHandler}
                  />
                  <DeviceWindow/>
            </div>
      )
}

export default Main;