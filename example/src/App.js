import React from "react"
import { Button, View } from "react-native"
import { NavigationContainer } from "@react-navigation/native"
import { createNativeStackNavigator } from "@react-navigation/native-stack"
import { UnityModule, UnityView } from "react-native-unity-play"

const Stack = createNativeStackNavigator()

export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Unity" component={Unity} />
        <Stack.Screen name="Main" component={Main} />
      </Stack.Navigator>
    </NavigationContainer>
  )
}

const Main = () => {
  return (
    <View style={{ flex: 1, alignItems: "center", justifyContent: "center" }}>
    </View>
  )
}

const Unity = ({ navigation }) => {
  return (
    <UnityView style={{ flex: 1, justifyContent: "flex-end" }} onMessage={onMessage}>
      <View style={{ flexDirection: "row", alignContent: "space-between", justifyContent: "center" }}>
        <Button title={"setColor"} onPress={async () => console.log(await cubeApi.setColor(randomColor()))} />
        <Button title={"toggleRotate"} onPress={async () => console.log(await cubeApi.toggleRotate())} />
        <Button title={"getAccount"} onPress={async () => console.log(await cubeApi.getAccount())} />
        <Button title={"fail"} onPress={async () => console.log(await cubeApi.fail())} />
        <Button
          title="home"
          onPress={() => navigation.navigate("Main")}
        />
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
