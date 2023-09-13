const connection = new WebSocket('ws://127.0.0.1:8044/websocket',
      "subprotocol.glucoreader.websocket");
export const onOpen = () => {
      console.log('Start function')
      connection.onopen = function () {
            console.log('Client connection opened');
            console.log('Subprotocol: ' + connection.protocol);
            console.log('Extensions: ' + connection.extensions);
      };
      console.log('End function')
}

export const onMessage = () => {
      let message = connection.onmessage = function (event) {
            return event.data
      };
}

export const onError = () => {
      connection.onerror = function (event) {
            console.log('Client error: ' + event);
      };
}

export const onClose = () => {
      connection.onclose = function (event) {
            console.log('Client connection closed: ' + event.code);
      };
}

export  const connectionListener = (handler, previousValue) => {
      connection.addEventListener('message', (event) => {
            //------------------------------------
            console.log("PreviousValue : " + previousValue)
            console.log();
            //------------------------------------
            const arr = JSON.parse(event.data);
            //handler(arr);
            console.log(event.data)
            const arr2 = [
                  {id: 1, name: "name1"}, {id: 2, name: "name2"}, {id: 3, name: "name3"}
            ]
            handler(arr2);
      })
}

