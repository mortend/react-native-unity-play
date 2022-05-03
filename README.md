# react-native-unity2

[![NPM package](https://img.shields.io/npm/v/react-native-unity2.svg?style=flat-square)](https://www.npmjs.com/package/react-native-unity2)
[![License: MIT](https://img.shields.io/github/license/fusetools/react-native-unity2.svg?style=flat-square)](LICENSE)

> Embed Unity content in your React Native app!

* Succesfully tested with Unity 2021 (LTS) and 2020 (LTS) on Android and iOS.

* Includes a complete, pre-configured [Unity project](unity) and an [example app](example).

This module started out as a fork of [react-native-unity-play](https://github.com/azesmway/react-native-unity-play), but is more or less rewritten with new and improved message passing (C# <=> TypeScript), fixing glitches and stability issues, and adding Swift support. That module was forked from other modules also, so kudos to all creators! 🤩

## Install

```shell
npm install react-native-unity2
```

## Usage

```javascript
import React from "react"
import { Button, View } from "react-native"
import { UnityModule, UnityView } from "react-native-unity2"

export default function App() {
  return (
    <UnityView style={{ flex: 1, justifyContent: "flex-end" }} onMessage={onMessage}>
      <View style={{ flexDirection: "row", alignContent: "space-between", justifyContent: "center" }}>
        <Button title={"setColor"} onPress={async () => console.log(await cubeApi.setColor(randomColor()))} />
        <Button title={"toggleRotate"} onPress={async () => console.log(await cubeApi.toggleRotate())} />
        <Button title={"getAccount"} onPress={async () => console.log(await cubeApi.getAccount())} />
        <Button title={"fail"} onPress={async () => console.log(await cubeApi.fail())} />
      </View>
    </UnityView>
  )
}

const onMessage = data => {
  console.log("Unity message: " + data)
}

const cubeApi = {
  setColor(color) {
    return UnityModule.callMethod("Cube", "setColorRN", color)
  },

  toggleRotate() {
    return UnityModule.callMethod("Cube", "toggleRotateRN")
  },

  getAccount() {
    return UnityModule.callMethod("Cube", "getAccountRN")
  },

  fail() {
    return UnityModule.callMethod("Cube", "failRN")
  },
}

const randomColor = () => {
  return `#${Math.floor(Math.random() * 16777215)
      .toString(16)
      .padStart(6, "0")}`
}
```

## Documentation

See [getting started](docs/getting-started.md) for information on how to set up your Unity project and React Native app.

See the included [Unity project](unity) and [RN app](example) for working examples.

See these source files for details about message passing in Unity:

  * [`RNBridge`](unity/Assets/RNUnity/RNBridge.cs)
  * [`RNPromise`](unity/Assets/RNUnity/RNPromise.cs)
  * [Usage examples](unity/Assets/Example/SpinCube.cs)

## Contributing

Please [report an issue](https://github.com/fusetools/react-native-unity2/issues) if you encounter a problem, or [open a pull request](https://github.com/fusetools/react-native-unity2/pulls) if you make a patch.
