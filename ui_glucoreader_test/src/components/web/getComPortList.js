import data from "./data.json";

//Список портов
let comPortList = [];
let url = 'http://127.0.0.1:8044/ports';
//Функция получения json c url
const getData = async (url) => {
      const data = await fetch(url);
      return data.json();
};

/*const getComPortList = () => {
      const arr = [];
      arr.push(...data);
      return arr;
}*/

const getComPortList = async () => {
      comPortList = await getData(url);
      console.log(comPortList);
}

export default getComPortList();