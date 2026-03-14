console.log("1. 同步代码开始");

async function asyncFunction() {
  console.log("2. async 函数开始");
  const result = await new Promise((resolve) => {
    setTimeout(() => {
      console.log("3. Promise 内部的 setTimeout 回调执行");
      resolve("Promise 已解决");
    }, 0);
    // console.log("3. Promise 内部的 setTimeout 回调执行");
    // resolve("Promise 已解决");
  });
  console.log("4. await 之后的代码执行，结果是:", result);
}

asyncFunction();

console.log("5. 同步代码结束");

// 事件循环后续处理
Promise.resolve().then(() => {
  console.log("6. 微任务队列中的 Promise 回调执行");
});

// 加一个微任务
Promise.resolve().then(() => {
  console.log("7. 最后一个微任务 Promise 回调执行");
});

setTimeout(() => {
  console.log("8. 宏任务队列中的 setTimeout 回调执行");
}, 0);
