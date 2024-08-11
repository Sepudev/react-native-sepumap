import {
  requireNativeComponent,
  UIManager,
  type ViewStyle,
} from 'react-native';

interface SepumapViewProps {
  style: ViewStyle;
}

const ComponentName = 'SepumapView';

export const SepumapView =
  UIManager.getViewManagerConfig(ComponentName) != null
    ? requireNativeComponent<SepumapViewProps>(ComponentName)
    : () => {
        throw new Error('No link');
      };
