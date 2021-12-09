# My City Report for Citizens API Server

## About this software
This is a server implementation of the service "My City Report for Citizens", available as [My City Report](https://www.mycityreport.jp/).

## Development Environment
- Java 17 or later
- Kotlin 1.6.0 or later
- Gradle 7.3 or later
- Docker & Docker Compose v2

## Launching the development environment
You can start it with Docker compose. Inside you will find the actual API server and a hot reload container.
This means that any changes you make to the source code will automatically be applied to the development server.

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

### ktlint について
ワイルドカードインポートを使っているとエラーになります。IntelliJ IDEA を使っている場合は `Preferences > Editor > Code Style > Kotlin > Imports` にて
`Top-Level Symbols` と `Java Statics and Enum Members` の設定を `Use single name import` に切り替えておいてください。
また、`Packages to Use Import With '*'` に `io.ktor.*` が含まれている場合はこちらのチェックも外しておいてください。

## 環境変数一覧
設定として注入できる環境変数の一覧は以下の表の通りです。

| 環境変数名 | 設定可能な値 | デフォルト値 | 説明 |
| --- | --- | --- | --- |
| DEVELOPMENT | `true`, `false` | `false` | 開発モードへの切り替えに使います。開発モードではホットリロードが有効になります。 |
| PORT | ポートとして利用可能な数値 | `8080` | 外向けのポート設定です。 |
| CORS_URLS | スキーマやポートを含むURL | `""` | CORS でアクセスを通したい URL をカンマ区切りで指定できます。何も指定しない場合全ての URL から受け付けます。 |

## License
The source code is licensed under the MIT License.
