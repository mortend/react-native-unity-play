import * as React from "react"
import type { ViewProperties } from "react-native"
import { requireNativeComponent, View, ViewPropTypes } from "react-native"

export class UnityAndroidView extends React.Component<ViewProperties> {
    static propTypes = {
        ...ViewPropTypes
    }

    render() {
        const { ...props } = this.props
        return (
            <View {...props}>
                <NativeUnityView
                    style={{ position: "absolute", left: 0, right: 0, top: 0, bottom: 0 }}
                />
                {this.props.children}
            </View>
        )
    }
}

// @ts-ignore
const NativeUnityView = requireNativeComponent<UnityAndroidViewProps>("UnityView", UnityAndroidView)
