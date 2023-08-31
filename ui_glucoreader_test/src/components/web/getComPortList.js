import data from "./data.json";

const getComPortList = () => {
      const arr = [];
      arr.push(...data);
      return arr;
}

export default getComPortList();