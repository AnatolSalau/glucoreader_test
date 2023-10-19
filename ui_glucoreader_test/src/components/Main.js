import {useEffect, useState} from "react";
import { FcOk } from 'react-icons/fc'
import style from './Main.module.css'
import Section from "./Section";
import ComPortList from "./comPortList/ComPortList";
import axios from 'axios';

let countRender = 0;

function Main({connection}) {

      let [deviceList, setDeviceList] = useState([]);

      useEffect(() => {
            connection.onopen = (ev) => {
                  console.log('Connection is open');
                  if (ev.data) {
                        console.log(ev.data)
                        let json = JSON.parse(ev.data);
                        console.log("__________________________________")
                        console.log(json.data);
                        console.log("__________________________________")
                        setDeviceList(json.data.deviceList);
                  }
            };
            connection.onmessage = (ev) => {
                  console.log('Message from server received');
                  let json = JSON.parse(ev.data);
                  console.log("__________________________________")
                  console.log("JSON : ")
                  console.log(json.data);

                  console.log("__________________________________")
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

      let [activeComPortName, setActiveComPortName] = useState("");

      console.log("Main countRender : " + countRender++);

      const setActiveDeviceNameHandler = (name) => {
                  if (name === activeComPortName) {
                        setActiveDeviceNameHandler("")

                  }
                  else {
                        setActiveComPortName(name);
                        connection.send(name);
                  }
      };
      console.log("activeComPortName : " + activeComPortName);

      return (
            <div className={style.main}>
                  <ComPortList
                        text={"Список COM портов:"}
                        deviceList={deviceList}
                        activeDeviceName={activeComPortName}
                        setActiveDeviceNameHandlerHandler={setActiveDeviceNameHandler}
                  />
                  <Section text={"Соединение:"}>
                        <FcOk/>
                  </Section>
                  <Section text={"Версия протокола:"}/>
                  <Section text={"Версия прибора:"}/>
                  <Section text={"Состояние прибора:"}/>
            </div>
      )
}

export default Main;