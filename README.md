# Github Issue Dumper

Githubで管理されたリポジトリのIssueをダウンロードします。
これはリポジトリマイニングのために作られたので、
簡単な項目しかダウンロードしません。
完全なバックアップ目的ではありません。

各Issueについて以下の項目をダウンロードします。

- ID
- State
- User Login Name
- Created At
- Closed At
- Labels
- Title

ダウンロードする際は、GithubAPIのレート制限に注意してください。

## ビルド
```
gradlew build
```

## 使い方
```
java -jar build/libs/github-issue-dumper-1.0-SNAPSHOT.jar user repository output [your-username:your-password]
```

- user : 対象リポジトリのユーザー名
- repository : 対象リポジトリのリポジトリ名
- output : 出力ファイル名
- you-username:your-password : （オプション）GitHubのAPIレート制限を緩和するために、GitHubのアカウント名とパスワードを指定する

## ライセンス

Public Domain (CC0) のもとで配布されます。

