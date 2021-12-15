# My City Report for Citizens API Server
## このソフトウェアについて
これは [My City Report](https://www.mycityreport.jp/) として提供しているサービス "My City Report for Citizens" のサーバー部分実装です。

## 開発環境
- Java 17 or later
- Kotlin 1.6.0 or later
- Gradle 7.3 or later
- Docker & Docker Compose v2

## 開発環境の起動
Docker compose にて起動することができます。内部には実際の API サーバーと、ホットリロード用のコンテナが建っています。
従って、ソースコードを変更すると自動的にその変更部分が開発サーバーに適用されるようになっています。

```bash
$ docker compose build
$ docker compose up -d
```

## 開発の流れ
まず GitHub Projects にタスクを登録して、それを Issue に変換してください。それに対して Pull Request を作成します。

各開発用ブランチは `develop` から切るようにして、`feature/foo` `fix/bar` のように命名するようにしてください。この名前で作ると GitHub Actions が走るようになっています。

なお、そのまま push すると lint や test に引っかかる可能性があります。Push 前にはそれらを実行しておくと良いでしょう。 

```bash
$ git switch -c feature/foo
$ ./gradlew ktlintFormat
$ ./gradlew test
$ git add .
$ git commit -m "message"
$ git push -u origin feature/foo
```

### テストの実行
実は Docker 上でも実行できます。将来的にはデータベースとの結合テストが行われる予定なので、この方法で実行することを推奨します。

```bash
$ docker compose exec builder gradle test
```

### ktlint について
ワイルドカードインポートを使っているとエラーになります。IntelliJ IDEA を使っている場合は `Preferences > Editor > Code Style > Kotlin > Imports` にて
`Top-Level Symbols` と `Java Statics and Enum Members` の設定を `Use single name import` に切り替えておいてください。
また、`Packages to Use Import With '*'` に `io.ktor.*` が含まれている場合はこちらのチェックも外しておいてください。

### Docker Image のビルドについて
Fat Jar をコピーする方式になっているので、事前に jar ファイルを作っておいてください。

```bash
$ ./gradlew shadowJar
$ docker image build -t mcr-api-server .
$ docker container run --rm -p 8080:8080 mcr-api-server
```

## 環境変数一覧
設定として注入できる環境変数の一覧は以下の表の通りです。

| 環境変数名 | 設定可能な値 | デフォルト値 | 説明 |
| --- | --- | --- | --- |
| DEVELOPMENT | `true`, `false` | `false` | 開発モードへの切り替えに使います。開発モードではホットリロードが有効になります。 |
| PORT | ポートとして利用可能な数値 | `8080` | 外向けのポート設定です。 |
| CORS_URLS | スキーマやポートを含む URL | `""` | CORS でアクセスを通したい URL をカンマ区切りで指定できます。何も指定しない場合全ての URL から受け付けます。 |
| PROXY_BASE_URL | プロキシ先の URL | `http://localhost` | `/proxy` エンドポイントにアクセスした際のプロキシ先 URL を指定できます。 |

## License
ソースコードについては MIT ライセンスを適用しています。
