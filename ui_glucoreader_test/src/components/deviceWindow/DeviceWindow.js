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
                                    <div style={{display: "flex", gap: 2}}>
                                          <div>
                                                <div style={{border: "1px solid black", margin: 2}}>
                                                      <Section text={"Соединение:"}>
                                                            <FcOk/>
                                                      </Section>
                                                      <Section text={`Время: ${data.dateTime}`}/>
                                                      <Section text={`Имя порта: ${data.comPortDto.name}`}/>
                                                      <Section text={`Описание порта: ${data.comPortDto.description}`}/>
                                                </div>
                                                <div style={{border: "1px solid black", margin: 2}}>
                                                      <Section text={"Версия протокола"}/>
                                                      <Section text={data &&
                                                            `Мин. вер = ${data.protocolVersionDto.versionHi}, 
                                          макс. вер = ${data.protocolVersionDto.versionLo}`
                                                      }
                                                      />
                                                </div>
                                                <div style={{border: "1px solid black", margin: 2}}>
                                                      <Section text={"Прибор"}/>
                                                      <Section text={data &&
                                                            `Тип прибора = ${data.deviceTypeDto.deviceType}`
                                                      }/>
                                                      <Section text={data &&
                                                            `ID прибора = ${data.deviceTypeDto.serialId}`
                                                      }/>
                                                      <Section text={data &&
                                                            `Мин. вер. прошивки = ${data.deviceTypeDto.swVersionLow}, 
                                          макс. вер. прошивки = ${data.deviceTypeDto.swVersionHigh}`
                                                      }
                                                      />
                                                      <Section text={data &&
                                                            `Вер. платы = ${data.deviceTypeDto.hwVersion}`
                                                      }/>

                                                </div>
                                                <div style={{border: "1px solid black", margin: 2}}>
                                                      <Section text={"Конвертер"}/>
                                                      <Section text={data &&
                                                            `Тип конвертера = ${data.converterType.deviceType}`
                                                      }/>
                                                      <Section text={data &&
                                                            `ID конвертера = ${data.converterType.serialId}`
                                                      }/>
                                                      <Section text={data &&
                                                            `Мин. вер. прошивки = ${data.converterType.swVersionLow}, 
                                          макс. вер. прошивки = ${data.converterType.swVersionHigh}`
                                                      }
                                                      />
                                                      <Section text={data &&
                                                            `Вер. платы = ${data.converterType.hwVersion}`
                                                      }/>

                                                </div>
                                                <div style={{border: "1px solid black", margin: 2}}>
                                                      <Section text={"Состояние"}/>
                                                      <Section text={data &&
                                                            `Температура = ${data.stateDto.temperature}`
                                                      }/>
                                                      <Section text={data &&
                                                            `Код ошибки = ${data.stateDto.errorCode}`
                                                      }/>
                                                      <Section text={data &&
                                                            `Батарея = ${data.stateDto.battery}`
                                                      }
                                                      />
                                                </div>
                                          </div>
                                          <div>
                                                <div style={{border: "1px solid black", margin: 2}}>
                                                      <Section text={"Значения:"}/>
                                                      {data.valueDtoList.map((value, key) => {
                                                            return <Section key={key} text={`
                                                            Номер ${value.id},
                                                            время: ${value.dateTime}, 
                                                            глюкоза:  ${value.glucose}, 
                                                            гематокрит: ${value.hematocrit},
                                                            температура: ${value.temperature},
                                                            состояние: ${value.state},
                                                            метка пользователя : ${value.stateUserMark}
                                                            `}/>
                                                      })}
                                                </div>
                                          </div>
                                    </div>

                              </>
                              : <>
                                    <Section text={"COM port не выбран"}/>
                                    <Section/>
                              </>
                  }
            </div>
      )
}

export default DeviceWindow;