import style from './ComPortList.module.css'
import CheckBoxList from "./CheckBoxList";
import {TbReload} from "react-icons/tb";
import {useState} from "react";
let countComPortList = 0;
function ComPortList({text, activeDeviceName, setActiveDeviceNameHandler,
                           deviceList, setDeviceListHandler, setDataTimeHandler, setConverterTypeHandler}) {
      console.log("countComPortList " + countComPortList++);
      let [inputData, setInputData] = useState({
            deviceType: "",
            id: "",
            hw: "",
            swLow: "",
            swHi: ""
      });
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
                        <div
                              onClick={() => {setDataTimeHandler()}}
                              style={{
                                    display: "flex",
                                    alignItems: "center",
                                    height: "30px",
                                    fontSize: "14px",
                                    backgroundColor: "lightgray",
                                    boxShadow: "rgba(0, 0, 0, 0.25) 0px 2px 4px",
                                    borderRadius: "4px",
                                    paddingLeft: "4px",
                                    paddingRight: "4px",
                                    marginBottom: "4px"
                              }}
                        >
                              Установить  время и дату
                        </div>
                        <div
                              style={{display: "flex", gap: "2px", marginBottom: "2px"}}
                        >
                              <input
                                    name="deviceType"
                                    onChange={(  event) => {
                                          inputData.deviceType = event.target.value;
                                          setInputData(inputData);
                                    }}
                                    style={{maxWidth: "8px"}}
                              />
                              <input
                                    name="id"
                                    onChange={(  event) => {
                                          inputData.id = event.target.value;
                                          setInputData(inputData);
                                    }}
                                    style={{maxWidth: "60px"}}
                              />
                              <input
                                    name="hw"
                                    onChange={(  event) => {
                                          inputData.hw = event.target.value;
                                          setInputData(inputData);
                                    }}
                                    style={{maxWidth: "8px"}}
                              />
                              <input
                                    name="swLow"
                                    onChange={(  event) => {
                                          inputData.swLow = event.target.value;
                                          setInputData(inputData);
                                    }}
                                    style={{maxWidth: "8px"}}
                              />
                              <input
                                    name="swHigh"
                                    onChange={(  event) => {
                                          inputData.swHi = event.target.value;
                                          setInputData(inputData);
                                    }}
                                    style={{maxWidth: "8px"}}
                              />
                        </div>

                        <div
                              onClick={() => {setConverterTypeHandler(inputData)}}
                              style={{
                                    display: "flex",
                                    alignItems: "center",
                                    height: "30px",
                                    fontSize: "14px",
                                    backgroundColor: "lightgray",
                                    boxShadow: "rgba(0, 0, 0, 0.25) 0px 2px 4px",
                                    borderRadius: "4px",
                                    paddingLeft: "4px",
                                    paddingRight: "4px"
                              }}
                        >
                              Установить тип конвертера
                        </div>
            </div>
      )
}

export default ComPortList;