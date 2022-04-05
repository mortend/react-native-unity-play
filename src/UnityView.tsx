import * as React from "react"
import type { ViewProperties } from "react-native"
import { Platform, ViewPropTypes } from "react-native"
import { UnityAndroidView } from "./UnityAndroidView"
import { UnityResponderView } from "./UnityResponderView"

export class UnityView extends React.Component<ViewProperties> {
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
