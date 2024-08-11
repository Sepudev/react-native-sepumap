import {
  requireNativeComponent,
  UIManager,
  type ViewStyle,
} from 'react-native';

interface Marker {
  latitude: number;
  longitude: number;
}
interface SepumapViewProps {
  style: ViewStyle;
  markers: Marker[];
}

const ComponentName = 'SepumapView';

export const SepumapView =
  UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<SepumapViewProps>(ComponentName)
    : () => {
        throw new Error('No link');
      };
