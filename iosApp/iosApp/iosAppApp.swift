//
//  iosAppApp.swift
//  iosApp
//
//  Created by HyeonSeok Cha on 7/31/25.
//

import SwiftUI
import shared

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        InitKoinKt.doInitKoin()
        window = UIWindow(frame: UIScreen.main.bounds)
        if let window = window {
           let uiController = UINavigationController( rootViewController: MainViewControllerKt.MainViewController())
            uiController.isNavigationBarHidden = true
            uiController.interactivePopGestureRecognizer?.isEnabled = true
            window.rootViewController = uiController
           
            window.makeKeyAndVisible()
        }
        return true
    }
}

extension UINavigationController: UIGestureRecognizerDelegate {
    override open func viewDidLoad() {
        super.viewDidLoad()
        interactivePopGestureRecognizer?.delegate = self
    }
        
    public func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        return viewControllers.count > 1
    }
}
