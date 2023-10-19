import {MdOutlineCheckBoxOutlineBlank} from 'react-icons/md';
import {MdOutlineCheckBox} from 'react-icons/md';
import style from "./CheckBox.module.css";

let checkBoxRenderCount = 0;

function CheckBox({id, name, description, devicePortName, setActiveDeviceNameHandler}) {
      console.log('name = ' + name);
      const changeStyleByClick = () => {
                 return ` ${
                       name === devicePortName
                             ? ` ${style.checkBox} ${style.active}`
                             : ` ${style.checkBox} `
                 } `
      }

      const changeIconByCLick = () => {
            // console.log("changeIconByCLick");
            return name === devicePortName
                  ? <MdOutlineCheckBox/>
                  : <MdOutlineCheckBoxOutlineBlank/>

      }
      // console.log("CheckBoxRenderCount : " + checkBoxRenderCount++);
      return (
            <div
                  className={changeStyleByClick()}
                  onClick={() => {
                        setActiveDeviceNameHandler(name);
                        changeStyleByClick();
                        // setPortIsActive(!portIsActive);
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