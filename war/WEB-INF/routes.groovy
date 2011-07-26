
get "/account/create", forward: "/unbord/account/accountCreate.groovy"
get "/account/logout", forward: "/unbord/account/accountLogOut.groovy"
get "/account/login", forward: "/unbord/account/accountLogIn.groovy"

get "/event/create", forward: "/unbord/event/eventCreate.groovy"

// routes for the blobstore service example
get "/upload",  forward: "/upload.gtpl"
get "/success", forward: "/success.gtpl"
get "/failure", forward: "/failure.gtpl"

get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png"