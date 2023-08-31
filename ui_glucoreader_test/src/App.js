import './App.css';
import Main from './components/Main'
import {memo} from "react";
function App() {

      const returnMain = memo(function returnMain() {
            return <Main/>
      });

      return (
            <div className="App">
                  {returnMain}
            </div>
      );
}

export default App;
