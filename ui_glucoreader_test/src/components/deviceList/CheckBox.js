import {MdOutlineCheckBoxOutlineBlank} from 'react-icons/md';
import {MdOutlineCheckBox} from 'react-icons/md';
import style from "./CheckBox.module.css";

function CheckBox({id, name, description, devicePortName, setActiveDeviceNameHandler}) {
      const changeStyleByClick = () => {
                 return ` ${
                       name === devicePortName
                             ? ` ${style.checkBox} ${style.active}`
                             : ` ${style.checkBox} `
                 } `
      }

      const changeIconByCLick = () => {
            return name === devicePortName
                  ? <MdOutlineCheckBox/>
                  : <MdOutlineCheckBoxOutlineBlank/>

      }
      return (
            <div
                  className={changeStyleByClick()}
                  onClick={() => {
                        setActiveDeviceNameHandler(name);
                        changeStyleByClick();
                  }}
            >
                  <div className={style.checkFieldEmpty}>
                        {changeIconByCLick()}
                  </div>
                  <div className={style.nameDescription}>
                        <div className={style.name}>
                              {name}
                        </div>
                        <div className={style.description}>
                              {description}
                        </div>
                  </div>
            </div>
      )
}

export default CheckBox;