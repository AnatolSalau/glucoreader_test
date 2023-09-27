import {useEffect, useState} from "react";
import { FcOk } from 'react-icons/fc'
import style from './Main.module.css'
import Section from "./Section";
import ComPortList from "./comPortList/ComPortList";
import axios from 'axios';

let countRender = 0;

function Main({connection}) {

      let [comPortList, setComPortList] = useState([
            {
                  id: 0,
                  name: "name",
                  description: "description",
                  protocolVersion: [

                  ]
            }
      ]);

      useEffect(() => {
            connection.onopen = (ev) => {
                  console.log('Connection is open');
                  if (ev.data) {
                        console.log(ev.data)
                        let json = JSON.parse(ev.data);
                        console.log("__________________________________")
                        console.log(json.data);
                        console.log("__________________________________")
                        setComPortList(json.data.comPortList);
                  }
            };
            connection.onmessage = (ev) => {
                  console.log('Message from server received');
                  let json = JSON.parse(ev.data);
                  console.log("__________________________________")
                  console.log("JSON : ")
                  console.log(json.data);

                  console.log("__________________________________")
                  setComPortList(json.data.comPortList);
            };
            connection.onclose = (ev) => {
                  console.log('Connection is close');
            };
      }, [setComPortList]);

      let [activeComPortName, setActiveComPortName] = useState("");

      console.log("Main countRender : " + countRender++);

      const setActiveComPortNameHandler = (name) => {
                  if (name === activeComPortName) {
                        setActiveComPortNameHandler("")

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
                        comPortList={comPortList}
                        activeComPortName={activeComPortName}
                        setActiveComPortNameHandler={setActiveComPortNameHandler}
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