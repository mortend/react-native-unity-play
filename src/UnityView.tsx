// eslint-disable-next-line import/no-extraneous-dependencies
import { Component } from "react"
import type { ViewProperties } from "react-native"
import { Platform, ViewPropTypes } from "react-native"
import { UnityAndroidView } from "./UnityAndroidView"
import { UnityResponderView } from "./UnityResponderView"

export class UnityView extends Component<ViewProperties> {
    static propTypes = {
        ...ViewPropTypes
    }

    render() {
        const { ...props } = this.props
        return Platform.OS === "android" ? (
            <UnityAndroidView {...props} />
        ) : (
            <UnityResponderView {...props} />
        )
    }
}
