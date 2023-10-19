import style from './ComPortList.module.css'
import CheckBoxList from "./CheckBoxList";
function ComPortList({text, activeDeviceName, setActiveDeviceNameHandlerHandler, deviceList}) {

      return (
            <div className={style.comPortList}>
                  <div className={style.text}>
                        {text}
                  </div>
                  <CheckBoxList
                        activeDeviceName={activeDeviceName}
                        setActiveDeviceNameHandler={setActiveDeviceNameHandlerHandler}
                        deviceList={deviceList}
                  />
            </div>
      )
}

export default ComPortList;