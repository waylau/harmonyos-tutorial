
import hilog from '@ohos.hilog';

class Logger {
  private domain: number;
  private prefix: string;
  private format: string = '%{public}s, %{public}s';

  /**
   * constructor.
   *
   * @param Prefix Identifies the log tag.
   * @param domain Domain Indicates the service domain, which is a hexadecimal integer ranging from 0x0 to 0xFFFFF.
   */
  constructor(prefix: string = 'MyApp', domain: number = 0xFF00) {
    this.prefix = prefix;
    this.domain = domain;
  }

  debug(...args: any[]): void {
    hilog.debug(this.domain, this.prefix, this.format, args);
  }

  info(...args: any[]): void {
    hilog.info(this.domain, this.prefix, this.format, args);
  }

  warn(...args: any[]): void {
    hilog.warn(this.domain, this.prefix, this.format, args);
  }

  error(...args: any[]): void {
    hilog.error(this.domain, this.prefix, this.format, args);
  }
}

export default new Logger('VideoPlayer', 0xFF00)