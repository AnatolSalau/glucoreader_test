const webSocket = new WebSocket('ws://127.0.0.1:8044/websocket',
      "subprotocol.glucoreader.websocket");
export const onOpen = () => {
      console.log('Start function')
      webSocket.onopen = function () {
            console.log('Client connection opened');
            console.log('Subprotocol: ' + webSocket.protocol);
            console.log('Extensions: ' + webSocket.extensions);
      };
      console.log('End function')
}

export const onMessage = () => {
      webSocket.onmessage = function (event) {
            console.log('Client received: ' + event.data);
      };
}

export const onError = () => {
      webSocket.onerror = function (event) {
            console.log('Client error: ' + event);
      };
}

export const onClose = () => {
      webSocket.onclose = function (event) {
            console.log('Client connection closed: ' + event.code);
      };
}

