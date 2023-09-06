import {MdOutlineCheckBoxOutlineBlank} from 'react-icons/md';
import {MdOutlineCheckBox} from 'react-icons/md';
import style from "./CheckBox.module.css";
import {useState} from "react";


let checkBoxRenderCount = 0;

function CheckBox({id, name, description, activeComPortName, setActiveComPortNameHandler}) {

      const changeStyleByClick = () => {
                 return ` ${
                       name === activeComPortName
                             ? ` ${style.checkBox} ${style.disabled}`
                             : ` ${style.checkBox} `
                 } `
      }

      const changeIconByCLick = () => {
            // console.log("changeIconByCLick");
            return name === activeComPortName
                  ? <MdOutlineCheckBox></MdOutlineCheckBox>
                  : <MdOutlineCheckBoxOutlineBlank></MdOutlineCheckBoxOutlineBlank>

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