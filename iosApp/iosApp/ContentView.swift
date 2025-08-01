//
//  ContentView.swift
//  iosApp
//
//  Created by HyeonSeok Cha on 7/31/25.
//

import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let viewController = MainViewControllerKt.MainViewController()
        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}

func PushController() {
   setNavigation()
   
   let contentView = ContentView().ignoresSafeArea(.keyboard)
   setRootController(for: UIHostingController(rootView: contentView))
}


private func setNavigation() {
    let appearance = UINavigationBarAppearance()
    appearance.configureWithOpaqueBackground()
    appearance.backgroundColor = .clear// UIColorManager().getAppDefaultOrange()
    appearance.titleTextAttributes = [.foregroundColor: UIColor.white]
    appearance.shadowColor = .clear
    UINavigationBar.appearance().standardAppearance = appearance
    UINavigationBar.appearance().scrollEdgeAppearance = appearance
    UINavigationBar.appearance().tintColor = .black
}

private func setRootController(for rootController: UIViewController) {
    if let window = UIApplication.shared.windows.first {
        // Set the new rootViewController of the window.
        if let root = window.rootViewController {
            window.rootViewController?.dismiss(animated: true, completion: {
                window.rootViewController = UINavigationController(rootViewController: rootController)
                window.makeKeyAndVisible()
             })
        } else {
            let navigationController = UINavigationController(rootViewController: rootController)
            
            navigationController.navigationBar.isHidden = true
            window.rootViewController = rootController
            window.makeKeyAndVisible()
        }
        
        // A mask of options indicating how you want to perform the animations.
        let options: UIView.AnimationOptions = .transitionCrossDissolve

        // The duration of the transition animation, measured in seconds.
        let duration: TimeInterval = 0.3

        // Creates a transition animation.
        // Though `animations` is optional, the documentation tells us that it must not be nil. ¯\_(ツ)_/¯
        UIView.transition(with: window, duration: duration, options: options, animations: {}, completion: { completed in
            // Do something on completion here
        })
    }
}
