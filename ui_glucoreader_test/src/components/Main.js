import {useEffect, useState} from "react";
import { FcOk } from 'react-icons/fc'
import style from './Main.module.css'
import Section from "./Section";
import ComPortList from "./comPortList/ComPortList";
import {connection} from "./ws/wsConnection"


let countRender = 0;

function Main() {
/*      let[connection, setConnection] = useState(new WebSocket('ws://127.0.0.1:8044/websocket',
            "subprotocol.glucoreader.websocket"));*/

      let [comPortList, setComPortList] = useState([
            {
                  id: 0,
                  name: "name",
                  description: "description"
            }
      ]);

      useEffect(() => {
            connection.onmessage = function (event) {
                  setComPortList(JSON.parse(event.data));
            };
      }, []);

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