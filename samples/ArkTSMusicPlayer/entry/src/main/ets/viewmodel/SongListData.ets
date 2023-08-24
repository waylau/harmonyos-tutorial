
import router from '@ohos.router'
import { RouterUrlConstants } from '../common/constants/RouterUrlConstants'
const optionList : OptionItem[] = [
  { image: $r('app.media.ic_collect'), text: $r('app.string.collect') },
  { image: $r('app.media.ic_download'), text: $r('app.string.download') },
  { image: $r('app.media.ic_comments'), text: $r('app.string.comment'), action: () => {
    router.pushUrl({
      url: RouterUrlConstants.MUSIC_COMMENT
    }, router.RouterMode.Single)
  }},
  { image: $r('app.media.ic_share'), text: $r('app.string.share') }
]

type OptionItem = {
  image: Resource;
  text: Resource;
  action?: () => void;
}

export { optionList, OptionItem }