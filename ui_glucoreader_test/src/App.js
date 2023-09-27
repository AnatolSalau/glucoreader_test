import './App.css';
import Main from './components/Main'
import {useState} from "react";

function App() {
      let [connection, setConnection] = new useState(new WebSocket('ws://127.0.0.1:8044/websocket',
            "subprotocol.glucoreader.websocket"))

      return (
            <div className="App">
                  <Main connection = {connection}/>
            </div>
      );
}

export default App;
