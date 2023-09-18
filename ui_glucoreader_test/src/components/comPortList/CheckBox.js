import {MdOutlineCheckBoxOutlineBlank} from 'react-icons/md';
import {MdOutlineCheckBox} from 'react-icons/md';
import style from "./CheckBox.module.css";

let checkBoxRenderCount = 0;

function CheckBox({id, name, description, activeComPortName, setActiveComPortNameHandler}) {

      const changeStyleByClick = () => {
                 return ` ${
                       name === activeComPortName
                             ? ` ${style.checkBox} ${style.active}`
                             : ` ${style.checkBox} `
                 } `
      }

      const changeIconByCLick = () => {
            // console.log("changeIconByCLick");
            return name === activeComPortName
                  ? <MdOutlineCheckBox/>
                  : <MdOutlineCheckBoxOutlineBlank/>

      }
      // console.log("CheckBoxRenderCount : " + checkBoxRenderCount++);
      return (
            <div
                  className={changeStyleByClick()}
                  onClick={() => {
                        setActiveComPortNameHandler(name);
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